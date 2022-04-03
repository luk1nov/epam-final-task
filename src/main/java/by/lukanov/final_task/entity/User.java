package by.lukanov.final_task.entity;

public class User extends AbstractEntity{
    private long id;
    private String email;
    private String password;
    private String name;
    private String surname;
    private Role role;
    private Status status;

    public User() {
    }

    public User(String email, String password, String name, String surname) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.role = Role.USER;
        this.status = Status.INACTIVE;
    }

    public enum Role{
        ADMIN,
        MANAGER,
        USER,
        GUEST
    }

    public enum Status{
        ACTIVE,
        INACTIVE,
        BLOCKED
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        if (id != user.id) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (surname != null ? !surname.equals(user.surname) : user.surname != null) return false;
        if (role != user.role) return false;
        return status == user.status;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", email='").append(email).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", surname='").append(surname).append('\'');
        sb.append(", role=").append(role);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }

    public static class UserBuilder{
        private final User user;

        public UserBuilder() {
            user = new User();
        }

        public UserBuilder id(long id){
            user.id = id;
            return this;
        }

        public UserBuilder name(String name){
            user.name = name;
            return this;
        }

        public UserBuilder surname(String surname){
            user.surname = surname;
            return this;
        }

        public UserBuilder email(String email){
            user.email = email;
            return this;
        }

        public UserBuilder password(String password){
            user.password = password;
            return this;
        }

        public UserBuilder role(Role role){
            user.role = role;
            return this;
        }

        public UserBuilder status(Status status){
            user.status = status;
            return this;
        }

        public User build(){
            return user;
        }
    }
}
