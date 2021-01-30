package ru.oogis.model.form;

public class FormAndIdClient<V> {

    private long idClient;
    private V body;

    public long getIdClient() {
        return idClient;
    }

    public void setIdClient(long idClient) {
        this.idClient = idClient;
    }

    public V getBody() {
        return body;
    }

    public void setBody(V body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "FormAndIdClient{" +
                "idClient=" + idClient +
                ", body=" + body +
                '}';
    }
}
