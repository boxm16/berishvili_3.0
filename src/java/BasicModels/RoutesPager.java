package BasicModels;

import java.util.ArrayList;

public final class RoutesPager {

    private ArrayList<String> routeNumbers;
    private ArrayList<String> dateStamps;
    private String currentRoute;
    private String display;
    private String type;

    public RoutesPager(String type) {
        this.currentRoute = "initial";
        this.type = type;
        this.routeNumbers = new ArrayList<>();
        this.dateStamps = new ArrayList<>();

    }

    public ArrayList<String> getRouteNumbers() {
        return routeNumbers;
    }

    public void setRouteNumbers(ArrayList<String> routeNumbers) {
        this.routeNumbers = routeNumbers;
    }

    public ArrayList<String> getDateStamps() {
        return dateStamps;
    }

    public void setDateStamps(ArrayList<String> dateStamps) {
        this.dateStamps = dateStamps;
    }

    public String getCurrentRoute() {
        if (currentRoute.equals("initial")) {
            if (this.routeNumbers.size() > 0) {
                return this.routeNumbers.get(0);
            } else {
                return null;
            }
        } else {
            return this.currentRoute = currentRoute;
        }
    }

    public void setCurrentRoute(String currentRoute) {
        this.currentRoute = currentRoute;
    }

    public String getDisplay() {

        int currentRouteIndex = routeNumbers.indexOf(getCurrentRoute());

        StringBuilder previousRouteNumberHTML = new StringBuilder();
        StringBuilder nextRouteNumberHTML = new StringBuilder();

        String previosRouteNumber = "";
        if (currentRouteIndex - 1 >= 0) {
            previosRouteNumber = routeNumbers.get(currentRouteIndex - 1);
            previousRouteNumberHTML = previousRouteNumberHTML.append("<a href=\"" + type + "Request.htm?requestedRoute=").append(previosRouteNumber).append("\"\">").append(previosRouteNumber).append("</a>");
            //  previousRouteNumberHTML = previousRouteNumberHTML.append("<a href=\"detailedRoutesRequest.htm?requestedRoute=").append(previosRouteNumber).append("\">").append(previosRouteNumber).append("</a>");
        }

        StringBuilder currentRouteNumberHTML = new StringBuilder("<a class=\"active\">").append(getCurrentRoute()).append("</a>");

        String nextRouteNumber = "";
        if (currentRouteIndex + 1 <= routeNumbers.size() - 1) {
            nextRouteNumber = routeNumbers.get(currentRouteIndex + 1);
            nextRouteNumberHTML = nextRouteNumberHTML.append("<a href=\"" + type + "Request.htm?requestedRoute=").append(nextRouteNumber).append("\"\">").append(nextRouteNumber).append("</a>");
        }

        StringBuilder stringBuilder = new StringBuilder("<div class=\"pagination\">");
        stringBuilder = stringBuilder.append(previousRouteNumberHTML);
        stringBuilder = stringBuilder.append(currentRouteNumberHTML);
        stringBuilder = stringBuilder.append(nextRouteNumberHTML);

        stringBuilder = stringBuilder.append("</div>");
        return stringBuilder.toString();

        /* if (type.equals("detailedRoutes")) {
            int currentRouteIndex = routeNumbers.indexOf(currentRoute);

            StringBuilder previousRouteNumberHTML = new StringBuilder();
            StringBuilder nextRouteNumberHTML = new StringBuilder();

            String previosRouteNumber = "";
            if (currentRouteIndex - 1 >= 0) {
                previosRouteNumber = routeNumbers.get(currentRouteIndex - 1);
                previousRouteNumberHTML = previousRouteNumberHTML.append("<a href=\"detailedRoutesRequest.htm?requestedRoute=").append(previosRouteNumber).append("\">").append(previosRouteNumber).append("</a>");
            }

            StringBuilder currentRouteNumberHTML = new StringBuilder("<a class=\"active\">").append(currentRoute).append("</a>");

            String nextRouteNumber = "";
            if (currentRouteIndex + 1 <= routeNumbers.size() - 1) {
                nextRouteNumber = routeNumbers.get(currentRouteIndex + 1);
                nextRouteNumberHTML = nextRouteNumberHTML.append("<a href=\"detailedRoutesRequest.htm?requestedRoute=").append(nextRouteNumber).append("\">").append(nextRouteNumber).append("</a>");
            }

            StringBuilder stringBuilder = new StringBuilder("<div class=\"pagination\">");
            stringBuilder = stringBuilder.append(previousRouteNumberHTML);
            stringBuilder = stringBuilder.append(currentRouteNumberHTML);
            stringBuilder = stringBuilder.append(nextRouteNumberHTML);

            stringBuilder = stringBuilder.append("</div>");
            return stringBuilder.toString();
        }
        if (type.equals("intervals")) {
            int currentRouteIndex = routeNumbers.indexOf(currentRoute);

            StringBuilder previousRouteNumberHTML = new StringBuilder();
            StringBuilder nextRouteNumberHTML = new StringBuilder();

            String previosRouteNumber = "";
            if (currentRouteIndex - 1 >= 0) {
                previosRouteNumber = routeNumbers.get(currentRouteIndex - 1);
                previousRouteNumberHTML = previousRouteNumberHTML.append("<a href=\"intervalsRequest.htm?requestedRoute=").append(previosRouteNumber).append("\">").append(previosRouteNumber).append("</a>");
            }

            StringBuilder currentRouteNumberHTML = new StringBuilder("<a class=\"active\">").append(currentRoute).append("</a>");

            String nextRouteNumber = "";
            if (currentRouteIndex + 1 <= routeNumbers.size() - 1) {
                nextRouteNumber = routeNumbers.get(currentRouteIndex + 1);
                nextRouteNumberHTML = nextRouteNumberHTML.append("<a href=\"intervalsRequest.htm?requestedRoute=").append(nextRouteNumber).append("\">").append(nextRouteNumber).append("</a>");
            }

            StringBuilder stringBuilder = new StringBuilder("<div class=\"pagination\">");
            stringBuilder = stringBuilder.append(previousRouteNumberHTML);
            stringBuilder = stringBuilder.append(currentRouteNumberHTML);
            stringBuilder = stringBuilder.append(nextRouteNumberHTML);

            stringBuilder = stringBuilder.append("</div>");
            return stringBuilder.toString();
        }
        if (type.equals("baseReturns")) {
            int currentRouteIndex = routeNumbers.indexOf(currentRoute);

            StringBuilder previousRouteNumberHTML = new StringBuilder();
            StringBuilder nextRouteNumberHTML = new StringBuilder();

            String previosRouteNumber = "";
            if (currentRouteIndex - 1 >= 0) {
                previosRouteNumber = routeNumbers.get(currentRouteIndex - 1);
                previousRouteNumberHTML = previousRouteNumberHTML.append("<a href=\"baseReturnsRequest.htm?requestedRoute=").append(previosRouteNumber).append("\">").append(previosRouteNumber).append("</a>");
            }

            StringBuilder currentRouteNumberHTML = new StringBuilder("<a class=\"active\">").append(currentRoute).append("</a>");

            String nextRouteNumber = "";
            if (currentRouteIndex + 1 <= routeNumbers.size() - 1) {
                nextRouteNumber = routeNumbers.get(currentRouteIndex + 1);
                nextRouteNumberHTML = nextRouteNumberHTML.append("<a href=\"baseReturnsRequest.htm?requestedRoute=").append(nextRouteNumber).append("\">").append(nextRouteNumber).append("</a>");
            }

            StringBuilder stringBuilder = new StringBuilder("<div class=\"pagination\">");
            stringBuilder = stringBuilder.append(previousRouteNumberHTML);
            stringBuilder = stringBuilder.append(currentRouteNumberHTML);
            stringBuilder = stringBuilder.append(nextRouteNumberHTML);

            stringBuilder = stringBuilder.append("</div>");
            return stringBuilder.toString();
        }
         */
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void addRouteNumber(String routeNumber) {
        if (!this.routeNumbers.contains(routeNumber)) {
            this.routeNumbers.add(routeNumber);
        }
    }

    public void addDateStamp(String dateStamp) {
        if (!this.dateStamps.contains(dateStamp)) {
            this.dateStamps.add(dateStamp);
        }
    }

}
