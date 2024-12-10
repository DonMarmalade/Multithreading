package lab4_expe;

public class Fish {
        //  <------ Variabile ------>
        private final String[] fish_name = new String[]{"Bream", "Common dace", "Gadus morhua", "Gilthead bream", "Grey gurnard", "Rutilus", "Salmon", "European eel", "Arctic char"};
        String name;
        double price;
        int rarity;
    
        //  <------ Setters ------>
        final void set_name(int nr) {
            this.name = fish_name[Math.max(nr % (fish_name.length - 1), 0)];
        }
    
        void set_price(double price) {
            this.price = price;
        }
    
        void set_rarity(int rarity) {
            this.rarity = rarity;
        }
    
        //  <------ Getters ------>
        String get_name() {
            return name;
        }
    
        double get_price() {
            return price;
        }
    
        int get_rarity() {
            return rarity;
        }
    
        //  <------ Display ------>
        public void display() {
            System.out.println("Fish name: " + name);
            System.out.println("FIsh price : " + price);
            System.out.println("Fish rarity: " + rarity);
            System.out.println("");
        }
    
        // <------ Constructor ------>
        Fish() {
            name = fish_name[(int)Math.round(Math.random() * (fish_name.length - 1))];
            price = Math.random() * 200;
            rarity = (int)Math.round(Math.random() * 5);
        }
}
