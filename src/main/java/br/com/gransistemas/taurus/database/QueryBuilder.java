package br.com.gransistemas.taurus.database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QueryBuilder {
    private List<String> fields = new ArrayList<>();
    private List<String> orderBy = new ArrayList<>();
    private List<String> groupBy = new ArrayList<>();
    private List<String> where = new ArrayList<>();
    private List<String> whereHas = new ArrayList<>();
    private List<String> joins = new ArrayList<>();
    private String distinct = "";
    private String table;
    private String paginate;

    public QueryBuilder() { }

    public QueryBuilder(String table) {
        this.table = table;
    }

    public QueryBuilder from(String table) {
        this.table = table;
        return this;
    }

    public QueryBuilder distinct() {
        distinct = "DISTINCT";
        return this;
    }

    public QueryBuilder select(String fields) {
        this.fields.addAll(Arrays.asList(fields.split(",")));
        return this;
    }

    public QueryBuilder select(String... fields) {
        this.fields.addAll(Arrays.asList(fields));
        return this;
    }


    public QueryBuilder where(String where){
        this.where.add(where);
        return this;
    }

    public QueryBuilder where(String field, Object value){
        this.where.add(String.format("%s = %s", field, value));
        return this;
    }

    public QueryBuilder where(String... where){
        this.where.addAll(Arrays.asList(where));
        return this;
    }

    public QueryBuilder whereHas(String where){
        this.where.add(String.format("EXISTS (%s)", where));
        return this;
    }

    public QueryBuilder innerJoin(String table, String where1, String where2){
        this.joins.add(String.format("INNER JOIN %s ON %s = %s", table, where1, where2));
        return this;
    }


    public QueryBuilder orderBy(String fields) {
        this.orderBy.addAll(Arrays.asList(fields.split(",")));
        return this;
    }

    public QueryBuilder orderBy(String... fields) {
        this.orderBy.addAll(Arrays.asList(fields));
        return this;
    }


    public QueryBuilder groupBy(String fields) {
        this.groupBy.addAll(Arrays.asList(fields.split(",")));
        return this;
    }

    public QueryBuilder groupBy(String... fields) {
        this.groupBy.addAll(Arrays.asList(fields));
        return this;
    }


    public QueryBuilder paginate(int page, int limit) {
        this.paginate = String.format("limit %s,%s", limit, limit * page);
        return this;
    }

    public String build() {
        StringBuilder sql = new StringBuilder();

        sql.append(String.format("SELECT %s %s FROM %s ",
            distinct, String.join(", ", fields), table
        ));

        if(!joins.isEmpty()){
            sql.append(String.join(" ", joins));
        }

        if(!where.isEmpty()){
            sql.append(String.format(" WHERE %s ",
                where.stream().map(e -> "(" + e + ")").collect(Collectors.joining(" AND "))
            ));
        }

        return sql.toString();
    }
}
