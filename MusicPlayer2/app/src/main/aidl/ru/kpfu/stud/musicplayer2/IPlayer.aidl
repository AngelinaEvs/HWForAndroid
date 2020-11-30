package ru.kpfu.stud.musicplayer2;

interface IPlayer {
    void play(in Song song);
    boolean isPlaying();
    void pause();
    Song getSong();
    int getCurrentTime();
    int getDuration();
    void seek(in int currentPosition);
    void continuaion();
    void next();
    void stop();
    void previous();
    boolean isEnd();
    void setVolumePlayer(in float volumeNum);
    void setCurrentSong(in Song song);
    void setCurrentSongFromBundle(in Bundle bundle);
    void setListener(SongCallback callback);
}

parcelable Song;

interface SongCallback {

    oneway void isFinish();
}