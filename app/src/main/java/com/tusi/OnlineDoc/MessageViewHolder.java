package com.tusi.OnlineDoc;

import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tusi.OnlineDoc.DataLists.ClinicFollowedList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "MessageViewHolder";

    TextView messageTextView;
    ImageView messageImageView;
    TextView messengerTextView;
    CircleImageView messengerImageView;

    public MessageViewHolder(View v) {
        super(v);
        messageTextView = (TextView) itemView.findViewById(R.id.messageTextView3);
//        messageImageView = (ImageView) itemView.findViewById(R.id.messageImageView);
//        messengerTextView = (TextView) itemView.findViewById(R.id.messengerTextView);
//        messengerImageView = (CircleImageView) itemView.findViewById(R.id.messengerImageView);
    }

    public void bindMessage(ClinicFollowedList friendlyMessage) {
        if (friendlyMessage.getName() != null) {
            messageTextView.setText(friendlyMessage.getName());

            messageTextView.setVisibility(TextView.VISIBLE);}
           // messageImageView.setVisibility(ImageView.GONE);}
//        } else if (friendlyMessage.getImageUrl() != null) {
//            String imageUrl = friendlyMessage.getImageUrl();
//            if (imageUrl.startsWith("gs://")) {
//                StorageReference storageReference = FirebaseStorage.getInstance()
//                        .getReferenceFromUrl(imageUrl);
//
//                storageReference.getDownloadUrl()
//                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(Uri uri) {
//                                String downloadUrl = uri.toString();
//                                Glide.with(messageImageView.getContext())
//                                        .load(downloadUrl)
//                                        .into(messageImageView);
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.w(TAG, "Getting download url was not successful.", e);
//                            }
//                        });
//            }
//            else {
////                Glide.with(messageImageView.getContext())
////                        .load(friendlyMessage.getImageUrl())
////                        .into(messageImageView);
//            }
//
//            messageImageView.setVisibility(ImageView.VISIBLE);
//            messageTextView.setVisibility(TextView.GONE);
//        }
    }
}