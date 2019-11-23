<!-- [START view] -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="cover-sec" style="background-image: url(${pageContext.request.contextPath}/ui/images/banner1.jpeg)"></div>
<main class="main">
    <div class="main-section">
        <div class="container">
            <section class="forum-page">
                <div class="container">
                    <div class="forum-questions-sec">
                        <div class="row">
                            <div class="col-lg-8">
                                <div class="forum-post-view">
                                    <div class="usr-question">
                                        <div class="usr_img">
											<img alt="ahhh" width="60" src="${fn:escapeXml(not empty post.imageUrl?post.imageUrl:'http://placekitten.com/g/60/60')}">
                                        </div>
                                        <div class="usr_quest">
                                            <h3>${fn:escapeXml(post.title)}</h3>
                                            <span><i class="fa fa-clock-o"></i>${fn:escapeXml(post.publishedDate)}</span>
                                            <ul class="quest-tags">
                                                <li><a href="#" title="">${fn:escapeXml(post.category)}</a></li>
                                            </ul>
											<p>
                                            	${fn:escapeXml(post.description)}
											</p>
                                            <div class="comment-section">
                                                <h3>3 Comments</h3>
                                                <div class="comment-sec">
                                                    <ul>
                                                        <li>
                                                            <div class="comment-list">
                                                                <div class="bg-img">
                                                                    <img src="http://via.placeholder.com/40x40" alt="">
                                                                </div>
                                                                <div class="comment">
                                                                    <h3>John Smith</h3>
                                                                    <span><img src="images/clock.png" alt=""> 3 min ago</span>
                                                                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse at libero elit. Mauris ultrices sed lorem nec efficitur.</p>
                                                                </div>
                                                            </div><!--comment-list end-->
                                                        </li>
                                                        <li>
                                                            <div class="comment-list">
                                                                <div class="bg-img">
                                                                    <img src="http://via.placeholder.com/40x40" alt="">
                                                                </div>
                                                                <div class="comment">
                                                                    <h3>John Doe</h3>
                                                                    <span><img src="images/clock.png" alt=""> 3 min ago</span>
                                                                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam luctus hendrerit metus, ut ullamcorper quam finibus at.</p>
                                                                </div>
                                                            </div><!--comment-list end-->
                                                        </li>
                                                        <li>
                                                            <div class="comment-list">
                                                                <div class="bg-img">
                                                                    <img src="http://via.placeholder.com/40x40" alt="">
                                                                </div>
                                                                <div class="comment">
                                                                    <h3>John Doe</h3>
                                                                    <span><img src="images/clock.png" alt=""> 3 min ago</span>
                                                                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam luctus hendrerit metus, ut ullamcorper quam finibus at.</p>
                                                                </div>
                                                            </div><!--comment-list end-->
                                                        </li>
                                                    </ul>
                                                </div><!--comment-sec end-->
                                            </div>
                                        </div><!--usr_quest end-->
                                    </div><!--usr-question end-->
                                </div><!--forum-post-view end-->
                                <div class="post-comment-box">
                                    <h3>03 Comments</h3>
                                    <div class="user-poster">
                                        <div class="usr-post-img">
                                            <img src="http://via.placeholder.com/40x40" alt="">
                                        </div>
                                        <div class="post_comment_sec">
                                            <form>
                                                <textarea placeholder="Your Answer"></textarea>
                                                <button type="submit">Post Answer</button>
                                            </form>
                                        </div><!--post_comment_sec end-->
                                    </div><!--user-poster end-->
                                </div><!--post-comment-box end-->
                                <div class="next-prev">
                                    <a href="#" title="" class="fl-left">Preview</a>
                                    <a href="#" title="" class="fl-right">Next</a>
                                </div><!--next-prev end-->
                            </div>
                            <div class="col-lg-4">
                                <div class="widget widget-adver">
                                    <img src="http://via.placeholder.com/370x270" alt="">
                                </div><!--widget-adver end-->
								
                                <div class="widget widget-user">
                                    <h3 class="title-wd">Top User of the Week</h3>
                                    <ul>
                                        <li>
                                            <div class="usr-msg-details">
                                                <div class="usr-ms-img">
                                                    <img src="http://via.placeholder.com/50x50" alt="">
                                                </div>
                                                <div class="usr-mg-info">
                                                    <h3>Jessica William</h3>
                                                    <p>Graphic Designer </p>
                                                </div><!--usr-mg-info end-->
                                            </div>
                                            <span><img src="images/price1.png" alt="">1185</span>
                                        </li>
                                        <li>
                                            <div class="usr-msg-details">
                                                <div class="usr-ms-img">
                                                    <img src="http://via.placeholder.com/50x50" alt="">
                                                </div>
                                                <div class="usr-mg-info">
                                                    <h3>John Doe</h3>
                                                    <p>PHP Developer</p>
                                                </div><!--usr-mg-info end-->
                                            </div>
                                            <span><img src="images/price2.png" alt="">1165</span>
                                        </li>
                                        <li>
                                            <div class="usr-msg-details">
                                                <div class="usr-ms-img">
                                                    <img src="http://via.placeholder.com/50x50" alt="">
                                                </div>
                                                <div class="usr-mg-info">
                                                    <h3>Poonam</h3>
                                                    <p>Wordpress Developer </p>
                                                </div><!--usr-mg-info end-->
                                            </div>
                                            <span><img src="images/price3.png" alt="">1120</span>
                                        </li>
                                        <li>
                                            <div class="usr-msg-details">
                                                <div class="usr-ms-img">
                                                    <img src="http://via.placeholder.com/50x50" alt="">
                                                </div>
                                                <div class="usr-mg-info">
                                                    <h3>Bill Gates</h3>
                                                    <p>C & C++ Developer </p>
                                                </div><!--usr-mg-info end-->
                                            </div>
                                            <span><img src="images/price4.png" alt="">1009</span>
                                        </li>
                                    </ul>
                                </div><!--widget-user end-->
                            </div>
                        </div>
                    </div><!--forum-questions-sec end-->
                </div>
            </section><!--forum-page end-->
        </div>
    </div>
</div>
<!-- [END view] -->
