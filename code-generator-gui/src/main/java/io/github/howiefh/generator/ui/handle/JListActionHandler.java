package io.github.howiefh.generator.ui.handle;

import io.github.howiefh.generator.common.util.StringUtils;
import io.github.howiefh.generator.ui.model.AbstractModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author fenghao, 2016/7/13
 * @version 1.0
 * @since 1.0
 */
public class JListActionHandler extends CommonHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(JListActionHandler.class);

    public void addItem(Object item, AbstractModel model, String listFieldName, JList jList) {
        try {
            if (item == null || model == null || listFieldName == null || jList == null) {
                return;
            }
            if (item instanceof String && StringUtils.isBlank((String)item)) {
                return;
            }

            List list = (List) invokeGetMethod(model, listFieldName);
            if (list == null) {
                list = new ArrayList();
            }

            if (list.contains(item)) {
                if (item instanceof Map.Entry) {
                    list.add(item);
                    // add new item to list
                    invokeSetMethod(model, listFieldName, list);
                }
                return;
            }

            list.add(item);

            // add new item to list
            invokeSetMethod(model, listFieldName, list);

            // select new item in list and scroll row to visible area
            int row = list.size() - 1;
            jList.setSelectedIndex(row);
            jList.scrollRectToVisible(jList.getCellBounds(row, row));
        } catch (Exception e) {
            LOGGER.warn("Add item error", e);
        }
    }

    public void deleteItem(AbstractModel model, String listFieldName, JList jList) {
        try {
            if (model == null || listFieldName == null || jList == null) {
                return;
            }
            int[] selectedRows = jList.getSelectedIndices();
            if (selectedRows.length == 0)
                return;

            List list = (List) invokeGetMethod(model, listFieldName);
            // remove items
            for (int i = selectedRows.length - 1; i >= 0; i--) {
                list.remove(selectedRows[i]);
            }

            invokeSetMethod(model, listFieldName, list);

            // select row
            if (list.size() > 0) {
                int newSel = Math.min(selectedRows[0], list.size() - 1);
                jList.setSelectedIndex(newSel);
                jList.scrollRectToVisible(jList.getCellBounds(newSel, newSel));
            }
        } catch (Exception e) {
            LOGGER.warn("Delete items error", e);
        }
    }
}
