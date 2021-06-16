package model;

import java.io.Serializable;

public class StudentTM implements Serializable {
    private String nic;
    private String fullName;
    private String address;

    public StudentTM() {
    }

    public StudentTM(String nic, String fullName, String address) {
        this.setNic(nic);
        this.setFullName(fullName);
        this.setAddress(address);
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "StudentTM{" +
                "nic='" + nic + '\'' +
                ", fullName='" + fullName + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
