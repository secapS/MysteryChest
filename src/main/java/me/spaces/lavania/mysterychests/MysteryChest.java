package me.spaces.lavania.mysterychests;

public class MysteryChest {

    private String name;
    private int maxSize;

    public MysteryChest(String name, int maxSize) {
        this.name = name;
        this.maxSize = maxSize;
    }

    public String getName() {
        return this.name;
    }

    public int getMaxSize() {
        return this.maxSize;
    }
}
