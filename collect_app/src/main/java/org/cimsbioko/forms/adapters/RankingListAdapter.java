/*
 * Copyright 2018 Nafundi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.cimsbioko.forms.adapters;

import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.javarosa.core.model.FormIndex;
import org.javarosa.core.model.SelectChoice;
import org.cimsbioko.forms.R;
import org.cimsbioko.forms.adapters.RankingListAdapter.ItemViewHolder;
import org.cimsbioko.forms.application.FormsApp;
import org.cimsbioko.forms.logic.FormController;
import org.cimsbioko.forms.utilities.ThemeUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RankingListAdapter extends Adapter<ItemViewHolder> {

    private final List<String> values;
    private final FormIndex formIndex;

    public RankingListAdapter(List<String> values, FormIndex formIndex) {
        this.values = new ArrayList<>(values);
        this.formIndex = formIndex;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.ranking_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, int position) {
        FormController formController = FormsApp.getInstance().getFormController();
        String itemName = formController != null
                ? formController.getQuestionPrompt(formIndex).getSelectChoiceText(getItem(formController, values.get(position)))
                : values.get(position);
        holder.textView.setText(itemName);
    }

    private SelectChoice getItem(FormController formController, String value) {
        for (SelectChoice item : formController.getQuestionPrompt(formIndex).getSelectChoices()) {
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        return null;
    }

    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(values, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public List<String> getValues() {
        return values;
    }

    public static class ItemViewHolder extends ViewHolder {

        final TextView textView;
        final ThemeUtils themeUtils;

        ItemViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.rank_item_text);
            textView.setTextSize(FormsApp.getQuestionFontsize());
            themeUtils = new ThemeUtils(itemView.getContext());
        }

        public void onItemSelected() {
            GradientDrawable border = new GradientDrawable();
            border.setColor(themeUtils.getRankItemColor());
            border.setStroke(10, themeUtils.getAccentColor());
            itemView.setBackground(border);
        }

        public void onItemClear() {
            itemView.setBackgroundColor(themeUtils.getRankItemColor());
        }
    }
}
