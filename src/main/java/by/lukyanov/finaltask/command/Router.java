package by.lukyanov.finaltask.command;

public class Router {
    public enum Type{
        FORWARD,
        REDIRECT
    }

    private String pagePath;
    private Type type;

    public Router() {
        this.type = Type.FORWARD;
    }

    public Router(String path) {
        this.type = Type.FORWARD;
        this.pagePath = path;
    }

    public String getPagePath() {
        return pagePath;
    }

    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
