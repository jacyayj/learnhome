package pro.haichuang.learn.home.ui.im.helper;

import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.api.model.chatroom.ChatRoomSessionCustomization;
import com.netease.nim.uikit.business.session.actions.BaseAction;

import java.util.ArrayList;

/**
 * UIKit自定义聊天室消息界面用法展示类
 * <p>
 * Created by huangjun on 2017/9/18.
 */

public class ChatRoomSessionHelper {

    public static void init() {
        registerViewHolders();
        NimUIKit.setCommonChatRoomSessionCustomization(getChatRoomSessionCustomization());
    }

    private static void registerViewHolders() {
//        NimUIKit.registerChatRoomMsgItemViewHolder(GuessAttachment.class, ChatRoomMsgViewHolderGuess.class);
    }

    private static ChatRoomSessionCustomization getChatRoomSessionCustomization() {
        ArrayList<BaseAction> actions = new ArrayList<>();
//        actions.add(new GuessAction());
        ChatRoomSessionCustomization customization = new ChatRoomSessionCustomization();
        customization.actions = actions;
        return customization;
    }
}
