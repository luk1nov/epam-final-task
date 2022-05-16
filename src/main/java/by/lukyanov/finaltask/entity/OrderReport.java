package by.lukyanov.finaltask.entity;

public class OrderReport extends AbstractEntity{
    private String photo;
    private String reportText;
    private OrderReportStatus reportStatus;
    private Order order;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getReportText() {
        return reportText;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        OrderReport that = (OrderReport) o;

        if (photo != null ? !photo.equals(that.photo) : that.photo != null) return false;
        if (reportText != null ? !reportText.equals(that.reportText) : that.reportText != null) return false;
        if (reportStatus != that.reportStatus) return false;
        return order != null ? order.equals(that.order) : that.order == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (photo != null ? photo.hashCode() : 0);
        result = 31 * result + (reportText != null ? reportText.hashCode() : 0);
        result = 31 * result + (reportStatus != null ? reportStatus.hashCode() : 0);
        result = 31 * result + (order != null ? order.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderReport{");
        sb.append("id='").append(this.getId()).append('\'');
        sb.append(", photo='").append(photo).append('\'');
        sb.append(", reportText='").append(reportText).append('\'');
        sb.append(", reportStatus=").append(reportStatus);
        sb.append(", order=").append(order);
        sb.append('}');
        return sb.toString();
    }
}
