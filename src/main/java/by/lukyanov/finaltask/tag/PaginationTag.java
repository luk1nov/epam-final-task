package by.lukyanov.finaltask.tag;

import by.lukyanov.finaltask.entity.CarCategory;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static by.lukyanov.finaltask.command.ParameterAttributeName.*;

public class PaginationTag extends SimpleTagSupport{
    private static final Logger logger = LogManager.getLogger();
    private static final Integer MIN_PAGE_NUMBER = 1;
    private String command;

    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        Integer totalPages = (Integer) getJspContext().findAttribute(PAGES_COUNT);
        if(totalPages != null && totalPages > 1 && command != null){
            String page = (String) getJspContext().findAttribute(RESULT_PAGE);
            Integer pageNumber = page == null || page.isBlank() ? MIN_PAGE_NUMBER : Integer.parseInt(page);
            out.println("<nav><ul class='pagination'>");
            for (int i = pageNumber - 2; i <= pageNumber + 2; i++) {
                if (i >= MIN_PAGE_NUMBER && i <= totalPages){
                    out.println(buildPaginationElement(i, pageNumber));
                }
            }
            out.println("</nav></ul>");
        } else {
            logger.warn("Pagination not printed");
        }
    }

    private String buildPaginationElement(int page, int currentPage){
        CarCategory category = (CarCategory) getJspContext().findAttribute(CAR_CATEGORY);
        StringBuilder sb = new StringBuilder();
        sb.append(page == currentPage ? "<li class='page-item disabled active'>" : "<li class='page-item'>")
            .append("<form action='/controller' method='POST' class='m-0'><input type='hidden' name='page' value='")
            .append(page)
            .append("'><input type='hidden' name='command' value='")
            .append(command)
            .append("'>");
        if(category != null){
            sb.append("<input type='hidden' name='carCategoryId' value='")
                .append(category.getId())
                .append("'>");
        }
        sb.append("<input class='page-link' type='submit' value='")
            .append(page)
            .append("'></form></li>");
        return sb.toString();
    }
}
