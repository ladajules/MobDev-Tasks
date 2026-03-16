package com.ladajules.mobdevtasks;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CategoryActivity extends AppCompatActivity {
    private Spinner spinner;
    private Spinner sizeFilterSpinner;
    private Spinner colorFilterSpinner;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private ItemAdapter itemAdapter;
    private ImageView ibBackToProfile;
    private List<Item> allItems;
    private String currentSort = "A-Z";
    private String currentQuery = "";
    private String currentSizeFilter = "All Sizes";
    private String currentColorFilter = "All Colors";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        spinner = findViewById(R.id.sCategories);
        sizeFilterSpinner = findViewById(R.id.sSizeFilter);
        colorFilterSpinner = findViewById(R.id.sColorFilter);
        recyclerView = findViewById(R.id.rvCategoryItems);
        searchView = findViewById(R.id.svSearch);
        ibBackToProfile = findViewById(R.id.ibBackToProfile);

        ibBackToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setupData();

        ArrayAdapter<CharSequence> sortAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.sorts,
                android.R.layout.simple_spinner_item
        );
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(sortAdapter);
        spinner.setSelection(2);

        ArrayAdapter<CharSequence> sizeAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.size_filters,
                android.R.layout.simple_spinner_item
        );
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeFilterSpinner.setAdapter(sizeAdapter);

        ArrayAdapter<CharSequence> colorAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.color_filters,
                android.R.layout.simple_spinner_item
        );
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorFilterSpinner.setAdapter(colorAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemAdapter = new ItemAdapter(new ArrayList<>());
        recyclerView.setAdapter(itemAdapter);

        applyFilterAndSearch();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentSort = parent.getItemAtPosition(position).toString();
                applyFilterAndSearch();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        sizeFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentSizeFilter = parent.getItemAtPosition(position).toString();
                applyFilterAndSearch();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        colorFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentColorFilter = parent.getItemAtPosition(position).toString();
                applyFilterAndSearch();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                currentQuery = newText.toLowerCase();
                applyFilterAndSearch();
                return true;
            }
        });


    }

    private void setupData() {
        allItems = new ArrayList<>();

        allItems.add(new Item("Apple", 5.99, "Medium", "Red"));
        allItems.add(new Item("Zebra", 7.99, "Large", "Black & White"));
        allItems.add(new Item("Banana", 2.99, "Medium", "Yellow"));
        allItems.add(new Item("Cherry", 2.99, "Small", "Red"));
        allItems.add(new Item("Orange", 3.99, "Medium", "Orange"));
        allItems.add(new Item("Grapes", 4.99, "Small", "Purple"));
        allItems.add(new Item("Watermelon", 6.99, "Large", "Green"));
        allItems.add(new Item("Mango", 7.99, "Medium", "Orange"));
        allItems.add(new Item("Pineapple", 8.99, "Large", "Yellow"));
        allItems.add(new Item("Strawberry", 9.99, "Small", "Red"));
        allItems.add(new Item("Blueberry", 10.99, "Small", "Blue"));
        allItems.add(new Item("Raspberry", 11.99, "Small", "Red"));
        allItems.add(new Item("Blackberry", 12.99, "Small", "Black"));
        allItems.add(new Item("Lemon", 1.99, "Small", "Yellow"));
        allItems.add(new Item("Lime", 1.99, "Small", "Green"));
        allItems.add(new Item("Peach", 2.99, "Medium", "Pink"));
        allItems.add(new Item("Pear", 3.99, "Medium", "Green"));
        allItems.add(new Item("Plum", 4.99, "Medium", "Purple"));
    }

    private void applyFilterAndSearch() {
        Map<String, Item> uniqueItems = new LinkedHashMap<>();
        for (Item item : allItems) {
            if (!uniqueItems.containsKey(item.getName().toLowerCase())) {
                uniqueItems.put(item.getName().toLowerCase(), item);
            }
        }
        List<Item> items = new ArrayList<>(uniqueItems.values());

        if (!currentQuery.isEmpty()) {
            items.removeIf(item -> !item.getName().toLowerCase().contains(currentQuery));
        }

        if (!currentSizeFilter.equals("All Sizes")) {
            items.removeIf(item -> !item.getSize().equals(currentSizeFilter));
        }

        if (!currentColorFilter.equals("All Colors")) {
            items.removeIf(item -> !item.getColor().equals(currentColorFilter));
        }

        switch (currentSort) {
            case "A-Z":
                Collections.sort(items, (a, b) -> a.getName().compareTo(b.getName()));
                break;
            case "Z-A":
                Collections.sort(items, (a, b) -> b.getName().compareTo(a.getName()));
                break;
            case "Lowest Price":
                items.sort((item1, item2) -> Double.compare(item1.getPrice(), item2.getPrice()));
                break;
            case "Highest Price":
                items.sort((item1, item2) -> Double.compare(item2.getPrice(), item1.getPrice()));
                break;
        }

        itemAdapter.updateItems(items);
    }

    public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
        private List<Item> items;
        private Map<String, Integer> colorMap;

        public ItemAdapter(List<Item> items) {
            this.items = new ArrayList<>(items);
            initColorMap();
        }

        private void initColorMap() {
            colorMap = new HashMap<>();
            colorMap.put("Red", Color.parseColor("#E53935"));
            colorMap.put("Green", Color.parseColor("#43A047"));
            colorMap.put("Blue", Color.parseColor("#1E88E5"));
            colorMap.put("Yellow", Color.parseColor("#FDD835"));
            colorMap.put("Orange", Color.parseColor("#FB8C00"));
            colorMap.put("Purple", Color.parseColor("#8E24AA"));
            colorMap.put("Pink", Color.parseColor("#D81B60"));
            colorMap.put("Black", Color.parseColor("#212121"));
            colorMap.put("Black & White", Color.parseColor("#757575"));
        }

        public void updateItems(List<Item> newItems) {
            this.items = new ArrayList<>(newItems);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_category, parent, false);
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            Item item = items.get(position);
            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        class ItemViewHolder extends RecyclerView.ViewHolder {
            TextView tvName, tvPrice, tvSize, tvColor;
            ImageView ivColorCircle;

            public ItemViewHolder(@NonNull View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.tvItemName);
                tvPrice = itemView.findViewById(R.id.tvItemPrice);
                tvSize = itemView.findViewById(R.id.tvItemSize);
                tvColor = itemView.findViewById(R.id.tvItemColor);
                ivColorCircle = itemView.findViewById(R.id.ivColorCircle);
            }

            public void bind(Item item) {
                tvName.setText(item.getName());
                tvPrice.setText(String.format("₱%.2f", item.getPrice()));
                tvSize.setText(item.getSize());
                tvColor.setText(item.getColor());

                Integer color = colorMap.get(item.getColor());
                if (color != null) {
                    ivColorCircle.setColorFilter(color, PorterDuff.Mode.SRC_IN);
                } else {
                    ivColorCircle.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
                }
            }
        }
    }
}
