package PacketGeneration.MessageHeaderGenerations;

/**
 * Stored the index and size of all info inside the Message Header
 */
public enum HeaderByteValues {
    SYNC_BYTE_1(0, 1),
    SYNC_BYTE_2(1, 1),
    MESSAGE_TYPE(2, 1),
    TIME_STAMP(3, 6),
    SOURCE_ID(9, 4),
    MESSAGE_BODY_LENGTH(13, 2);


    private int index;
    private int size;

    HeaderByteValues(int index, int size){
        this.index= index;
        this.size = size;
    }

    public int getIndex(){
        return index;
    }

    public int getSize() {
        return size;
    }

}
