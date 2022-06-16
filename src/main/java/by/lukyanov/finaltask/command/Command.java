package by.lukyanov.finaltask.command;

import by.lukyanov.finaltask.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public interface Command {
    Router execute(HttpServletRequest request) throws CommandException;

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
