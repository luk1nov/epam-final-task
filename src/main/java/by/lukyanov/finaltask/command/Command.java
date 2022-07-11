package by.lukyanov.finaltask.command;

import by.lukyanov.finaltask.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Command interface.
 */
public interface Command {
    /**
     * Process request, calls required service method and create Router object.
     *
     * @param request HttpServletRequest
     * @return router that contains next page path and RouterType (REDIRECT, FORWARD)
     * @see Router
     * @see Router.Type
     * @throws CommandException if there is a problem during command execute
     */
    Router execute(HttpServletRequest request) throws CommandException;

    /**
     * Builds url with GET query attributes
     *
     * @param url page path
     * @param attributeName attribute name
     * @param attributeValue attribute value
     * @return prepared link with query attributes
     */
    default String generateUrlWithAttr(String url, String attributeName, String attributeValue){
        StringBuilder stringBuilder = new StringBuilder()
                .append(url);
        if(attributeName != null && attributeValue != null && !attributeName.isBlank() && !attributeValue.isBlank()){
            stringBuilder.append(attributeName)
                    .append(attributeValue);
        }
        return stringBuilder.toString();
    }
}
