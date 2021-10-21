package hr.fer.zavrsni.model;

public class FormWorker {

    private String email;

    public FormWorker() {
    }

    public FormWorker(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "FormWorker{" +
                "email='" + email + '\'' +
                '}';
    }
}
