package by.lukyanov.finaltask.entity;

public final class CarInfo extends AbstractEntity{
    private Double acceleration;
    private Integer power;
    private Drivetrain drivetrain;

    public enum Drivetrain{
        AWD, FWD, RWD
    }

    public CarInfo() {
    }

    public CarInfo(Double acceleration, Integer power, Drivetrain drivetrain) {
        this.acceleration = acceleration;
        this.power = power;
        this.drivetrain = drivetrain;
    }

    public Double getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Double acceleration) {
        this.acceleration = acceleration;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public Drivetrain getDrivetrain() {
        return drivetrain;
    }

    public void setDrivetrain(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CarInfo carInfo = (CarInfo) o;
        if (acceleration != null ? !acceleration.equals(carInfo.acceleration) : carInfo.acceleration != null)
            return false;
        if (power != null ? !power.equals(carInfo.power) : carInfo.power != null) return false;
        return drivetrain == carInfo.drivetrain;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (acceleration != null ? acceleration.hashCode() : 0);
        result = 31 * result + (power != null ? power.hashCode() : 0);
        result = 31 * result + (drivetrain != null ? drivetrain.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CarInfo{");
        sb.append("acceleration=").append(acceleration);
        sb.append(", power=").append(power);
        sb.append(", drivetrain=").append(drivetrain);
        sb.append('}');
        return sb.toString();
    }
}
