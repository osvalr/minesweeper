package com.osvalr.minesweeper.domain;

public class Field {
    private Position[][] field;


    public Position[][] getField() {
        return field;
    }

    public void setField(Position[][] field) {
        this.field = field;
    }

//    private final static String POSITION_DELIMITER = "|";

//    public String getTextRepresentation() {
//        StringBuffer buffer = new StringBuffer();
//        for (Position[] row : field) {
//            buffer.append(POSITION_DELIMITER +
//                    Joiner.on(POSITION_DELIMITER)
//                            .join(Arrays.stream(row)
//                                    .map(Position::getValue)
//                                    .collect(Collectors.toList())) +
//                    POSITION_DELIMITER + "\n");
//        }
//        return buffer.toString();
//    }
}
