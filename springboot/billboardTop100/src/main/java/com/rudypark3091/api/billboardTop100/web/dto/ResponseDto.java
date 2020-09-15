package com.rudypark3091.api.billboardTop100.web.dto;

public class ResponseDto {
    private final int rank;
    private final String artist;
    private final String song;

    public ResponseDto(int rank, String artist, String song) {
        this.rank = rank;
        this.artist = artist;
        this.song = song;
    }

    public int getRank() {
        return this.rank;
    }

    public String getArtist() {
        return this.artist;
    }

    public String getSong() {
        return this.song;
    }
}
