package com.aucegypt.ispend;

import android.provider.BaseColumns;

public final class AppContract {

    private AppContract() {}

    public static class EntriesTable implements BaseColumns
    {
        public static final String TABLE_NAME = "entries";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_NOTES = "notes";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_METHOD = "method";
        public static final String COLUMN_VALUE = "value";
        public static final String COLUMN_EXPSAV = "expsav";
        public static final String COLUMN_STORE = "store";
        public static final String COLUMN_DATES = "dates";
    }
}
