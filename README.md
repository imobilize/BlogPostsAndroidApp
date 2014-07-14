BlogPostsAndroidApp
===================

This is the Android application corresponding to the website BlogPosts.

With this Android application you can read the articles published in the website, and also register to be kept up to date of the new articles published under your favourite categories.

The home activity shows a preview of the latest added articles, with its picture, title, author, creation date and description. Each of previews is a clickable item which leads you to a new activity in which you can read the selected article in full detail. From this article activity you can navigate backwards to the home activity.

The home activity is built of fragments. It contains two menus, a slide menu and a top menu. From the slide menu you can navigate through the home activity, accesing to the subscribe form and to an information page, with information about the application. The top menu shows your current location within the application and holds a dropdown menu with all existing categories. With this menu you can filter by categoriy the articles that you want to see displayed on the home activity.

With the subscribe form you can register to receive notifications everytime a new article is published on the website.
You only have to choose your favourite categories and press Subscribe! Once a new article is published, you'll be
inmediately notified with a message containing the name of the new article and the related category/ies. You'll see the application's icon displayed in the notification bar, and then the message with the new article information. Clicking on the message you'll be lead directly to detailed view of the new article.

DEPENDENCIES
============

* volley.jar
* gcm.jar




