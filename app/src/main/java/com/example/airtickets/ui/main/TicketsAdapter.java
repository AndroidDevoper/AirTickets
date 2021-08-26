package com.example.airtickets.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.airtickets.databinding.ItemTicketBinding;
import com.example.airtickets.model.Ticket;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class TicketsAdapter extends BaseAdapter {

    private List<Ticket> list;
    private LayoutInflater inflater;

    public TicketsAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public void setList(List<Ticket> list) {
        this.list = list;
    }

    public void sortDate() {
        if (list == null)
            return;
        Collections.sort(list, new Comparator<Ticket>() {
            DateFormat f = new SimpleDateFormat("dd/MM/yyyy");
            @Override
            public int compare(Ticket o1, Ticket o2) {
                try {
                    return f.parse(o1.getDate()).compareTo(f.parse(o2.getDate()));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });
        notifyDataSetChanged();
    }

    public void sortCostMax() {
        Collections.sort(list, new Comparator<Ticket>() {
            @Override
            public int compare(Ticket o1, Ticket o2) {
                if (o1.getCost() > o2.getCost())
                    return -1;
                else
                    return 1;
            }
        });
        notifyDataSetChanged();
    }

    public void sortCostMin() {
        Collections.sort(list, new Comparator<Ticket>() {
            @Override
            public int compare(Ticket o1, Ticket o2) {
                if (o1.getCost() < o2.getCost())
                    return -1;
                else
                    return 1;
            }
        });
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ItemTicketBinding binding = ItemTicketBinding.inflate(inflater);
        binding.itemTicketTvFrom.setText(list.get(i).getFrom());
        binding.itemTicketTvDest.setText(list.get(i).getDest());
        binding.itemTicketTvDate.setText(list.get(i).getDate());
        binding.itemTicketTvCost.setText(String.valueOf(list.get(i).getCost()));
        return binding.getRoot();
    }
}
