package model;

public class OrderList {
    private OrderInList[] orders;
    private PageInfo pageInfo;
    private AvailableStations[] availableStations;

    public boolean isNotEmpty(){
        return orders != null & pageInfo != null & availableStations !=null;
    }
}
