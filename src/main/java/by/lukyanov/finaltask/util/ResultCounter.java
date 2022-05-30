package by.lukyanov.finaltask.util;

public class ResultCounter {
    public static final int ROWS_PER_PAGE = 6;
    private int page;

    public ResultCounter(int page) {
        this.page = page;
    }

    public Integer offset(){
        return (page - 1) * ROWS_PER_PAGE;
    }

    public static Integer countPages(int rows){
        double pages = rows;
        pages /= ROWS_PER_PAGE;
        return (int) Math.ceil(pages);
    }
}
