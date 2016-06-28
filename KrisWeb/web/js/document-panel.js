$(document).ready(function() {
    $('#kris-panel-document-tabs > ul > li > a').click(function(event){
        event.preventDefault();//stop browser to take action for clicked anchor

        //get displaying tab content jQuery selector
        var active_tab_selector = $('#kris-panel-document-tabs > ul > li.active > a').attr('href');

        //find actived navigation and remove 'active' css
        var actived_nav = $('#kris-panel-document-tabs > ul > li.active');
        actived_nav.removeClass('active');

        //add 'active' css into clicked navigation
        $(this).parents('li').addClass('active');

        //hide displaying tab content
        $(active_tab_selector).removeClass('visible');
        $(active_tab_selector).addClass('gone');

        //show target tab content
        var target_tab_selector = $(this).attr('href');
        $(target_tab_selector).removeClass('gone');
        $(target_tab_selector).addClass('visible');
    });
});