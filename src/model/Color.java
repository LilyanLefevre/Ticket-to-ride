public enum Color {
    RED,
    BLACK,
    BLUE,
    GREEN,
    RAINBOW,
    ORANGE,
    PURPLE,
    WHITE,
    YELLOW,
    GREY;

    /**
     * Returns the name of this enum constant, as contained in the
     * declaration.  This method may be overridden, though it typically
     * isn't necessary or desirable.  An enum type should override this
     * method when a more "programmer-friendly" string form exists.
     *
     * @return the name of this enum constant
     */
    @Override
    public String toString() {
        return this.name();
    }
}
