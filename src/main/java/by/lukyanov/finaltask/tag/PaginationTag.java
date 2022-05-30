package by.lukyanov.finaltask.tag;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static by.lukyanov.finaltask.command.ParameterAttributeName.PAGES_COUNT;
import static by.lukyanov.finaltask.command.ParameterAttributeName.RESULT_PAGE;

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
        StringBuilder sb = new StringBuilder();
        sb.append(page == currentPage ? "<li class='page-item disabled active'>" : "<li class='page-item'>");
        sb.append("<form action='/controller' method='POST' class='m-0'>");
        sb.append("<input type='hidden' name='page' value='");
        sb.append(page);
        sb.append("'>");
        sb.append("<input type='hidden' name='command' value='");
        sb.append(command);
        sb.append("'>");
        sb.append("<input class='page-link' type='submit' value='");
        sb.append(page);
        sb.append("'>");
        sb.append("</form>");
        sb.append("</li>");
        return sb.toString();
    }
}
