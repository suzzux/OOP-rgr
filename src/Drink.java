/**
 * Клас, що представляє напої в автоматі. Наслідує Product і реалізує шаблон Builder.
 */
public class Drink extends Product {
    private final double volume; // Об'єм в літрах

    /**
     * Внутрішній статичний клас Builder для створення об'єктів Drink.
     */
    public static class Builder extends Product.Builder<Builder> {
        private double volume = 0.5; // Об'єм за замовчуванням

        /**
         * Конструктор з обов'язковими параметрами.
         * @param name Назва напою.
         * @param price Ціна напою.
         */
        public Builder(String name, double price) {
            super(name, price);
        }

        /**
         * Встановлює об'єм напою.
         * @param val Об'єм в літрах.
         * @return Поточний екземпляр Builder.
         */
        public Builder volume(double val) {
            this.volume = val;
            return this;
        }

        @Override
        public Drink build() {
            return new Drink(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    private Drink(Builder builder) {
        super(builder);
        this.volume = builder.volume;
    }

    public double getVolume() {
        return volume;
    }

    @Override
    public String getDescription() {
        return String.format("Напій: %s, Об'єм: %.1fл, Ціна: %.2f грн", getName(), getVolume(), getPrice());
    }
}
