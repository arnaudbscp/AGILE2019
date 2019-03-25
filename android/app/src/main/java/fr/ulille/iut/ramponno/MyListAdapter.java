package fr.ulille.iut.ramponno;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyListAdapter extends ArrayAdapter<Item> {
    //items est la liste des models à afficher
    public MyListAdapter(Context context, List<Item> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.agenda_items,parent, false);
        }

        TweetViewHolder viewHolder = (TweetViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new TweetViewHolder();
            viewHolder.pseudo = (TextView) convertView.findViewById(R.id.Titre);
            viewHolder.text = (TextView) convertView.findViewById(R.id.description);
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.icone);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Item> tweets
        Item item = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.pseudo.setText(item.getPseudo());
        viewHolder.text.setText(item.getText());
        viewHolder.avatar.setImageResource(item.getColor());

        return convertView;
    }

    private class TweetViewHolder{
        public TextView pseudo;
        public TextView text;
        public ImageView avatar;
    }
}
