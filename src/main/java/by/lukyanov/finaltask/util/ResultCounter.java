package by.lukyanov.finaltask.util;

public class ResultCounter {
    private final int postsPerPage;
    private final int page;

    public ResultCounter(int page, int postsPerPage) {
        this.page = page;
        this.postsPerPage = postsPerPage;
    }

    public Integer offset(){
        return (page - 1) * postsPerPage;
    }

    public static Integer countPages(int rows, int postsPerPage){
        double pages = rows;
        pages /= postsPerPage;
        return (int) Math.ceil(pages);
    }
}
