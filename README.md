BlogPostsAndroidApp
===================

This is the Android application corresponding to the website [BlogPosts](https://github.com/imobilize/BlogPostsWeb).

With this Android application you can read the articles published in the website, and also register to be kept up to date of the new articles published under your favourite categories.

The home activity shows a preview of the latest added articles, with its picture, title, author, creation date and description. Each of previews is a clickable item which leads you to a new activity in which you can read the selected article in full detail. From this article activity you can navigate backwards to the home activity.

The home activity is built of fragments. It contains two menus, a slide menu and a top menu. From the slide menu you can navigate through the home activity, accesing to the subscribe form and to an information page, with information about the application. The top menu shows your current location within the application and holds a dropdown menu with all existing categories. With this menu you can filter by categoriy the articles that you want to see displayed on the home activity.

With the subscribe form you can register to receive notifications everytime a new article is published on the website.
You only have to choose your favourite categories and press Subscribe! Once a new article is published, you'll be
inmediately notified with a message containing the name of the new article and the related category/ies. You'll see the application's icon displayed in the notification bar, and then the message with the new article information. Clicking on the message you'll be lead directly to detailed view of the new article.

DEPENDENCIES
============

* Google Cloud Messaging (GCM)
* Volley

LICENSE
=======
Copyright 2014 iMobilize Ltd.

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
