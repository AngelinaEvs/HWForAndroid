package ru.kpfu.stud.musicplayer2

import kotlin.collections.ArrayList

object SongRepository {
    var songRepository: ArrayList<Song> = ArrayList()
    var idPlayNow: Int = -1
    var idPlayOld: Int = -1

    init {
        with(songRepository) {
            add(Song("0", "ABBA", "Happy New Year", R.drawable.abba_hny, R.raw.abba))
            add(Song("1", "George Michael", "Last Christmas", R.drawable.gm_lc, R.raw.george_michael_merry_christmas))
            add(Song("2", "Дмитрий Маликой и Дайкири", "Снежинка", R.drawable.time_12, R.raw.mal_dayrkiri_12))
            add(Song("3", "Руки вверх", "Новый год", R.drawable.rv_ny, R.raw.rv_ny))
            add(Song("4", "Верка Сердючка", "Новогодняя песня", R.drawable.vs_ng, R.raw.vs_ny))
            add(Song("5", "Блестящие", "Часики", R.drawable.clock, R.raw.blest_ny))
            add(Song("6", "Дискотека авария", "Новый год к нам мчится", R.drawable.dav_ny, R.raw.da_ny))
            add(Song("7", "Аркадий Хоралов", "Новогодние игрушки", R.drawable.ah_toys, R.raw.ah_toys))
        }
    }

}