package com.example.techware.Model;

public class ComputerComponentValidator {
    public void validate(ComputerComponent component) throws Exception{
        if(component.getName() == null || component.getName().isBlank())
            throw new Exception("Invalid product name");

        if(component.getCategory() == null || component.getCategory().isBlank())
            throw new Exception("Invalid product category");

        if(component.getManufacturer() == null || component.getManufacturer().isBlank())
            throw new Exception("Invalid manufacturer name");

        if(component.getReleaseDate() == null)
            throw new Exception("Invalid date");

        if(component.getQuantity() < 0)
            throw new Exception("Invalid quantity; only positive integers are accepted");

        if(component.getPrice() < 0.0)
            throw new Exception("Invalid price; only positive numbers are accepted");
    }
}
