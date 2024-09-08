package com.doanAndroid.myapplication;

public class BookTicketItem {
    private String filmName;
    private String seatNumber;
    private String totalPrice;

    public BookTicketItem(String filmName, String seatNumber, String totalPrice) {
        this.filmName = filmName;
        this.seatNumber = seatNumber;
        this.totalPrice = totalPrice;
    }

    // Getter và setter cho các thuộc tính

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        // Định dạng hiển thị cho ListView
        return filmName + " - " + seatNumber + " - " + totalPrice;
    }
}