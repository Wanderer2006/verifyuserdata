package tech.elfin.verifyuserdata.model;

import java.io.Serializable;

/**
 * POJO-класс для десериализации JSON-ответа сервиса DaData
 */
public class VerifyDaDataResponse implements Serializable {
    private String source;
    private String series;
    private String number;
    private int qc;

    public VerifyDaDataResponse() {
    }

    public VerifyDaDataResponse(String source, String series, String number, int qc) {
        this.source = source;
        this.series = series;
        this.number = number;
        this.qc = qc;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getQc() {
        return qc;
    }

    public void setQc(int qc) {
        this.qc = qc;
    }

    @Override
    public String toString() {
        return "VerifyDaDataResponse{" +
                "source='" + source + '\'' +
                ", series='" + series + '\'' +
                ", number='" + number + '\'' +
                ", qc=" + qc +
                '}';
    }
}