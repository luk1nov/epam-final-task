package by.lukyanov.finaltask.entity;

import java.util.Optional;

public class OrderReport extends AbstractEntity{
    private String photo;
    private String reportText;
    private OrderReportStatus reportStatus;

    public OrderReport() {
    }

    public OrderReport(long id) {
        super(id);
    }

    public OrderReport(String photo, String reportText, OrderReportStatus reportStatus) {
        this.photo = photo;
        this.reportText = reportText;
        this.reportStatus = reportStatus;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Optional<String> getReportText() {
        return Optional.ofNullable(reportText);
    }

    public void setReportText(String reportText) {
        this.reportText = reportText;
    }

    public OrderReportStatus getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(OrderReportStatus reportStatus) {
        this.reportStatus = reportStatus;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        OrderReport report = (OrderReport) o;

        if (photo != null ? !photo.equals(report.photo) : report.photo != null) return false;
        if (reportText != null ? !reportText.equals(report.reportText) : report.reportText != null) return false;
        return reportStatus == report.reportStatus;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (photo != null ? photo.hashCode() : 0);
        result = 31 * result + (reportText != null ? reportText.hashCode() : 0);
        result = 31 * result + (reportStatus != null ? reportStatus.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderReport{");
        sb.append("id='").append(this.getId()).append('\'');
        sb.append(", photo='").append(photo).append('\'');
        sb.append(", reportText='").append(reportText).append('\'');
        sb.append(", reportStatus=").append(reportStatus);
        sb.append('}');
        return sb.toString();
    }
}
