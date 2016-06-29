/**
 * Created by Mohru on 2016-06-29.
 */
$(document).ready(function() {

    var types = document.getElementsByClassName('type-input-lg');

    for(i=0; i < types.length; i++)
    {
       types[i].addEventListener('click', function() {
           document.getElementById('item-type').value = (this.textContent || this.innerText);
       }, false);
    }

    var sizes = document.getElementsByClassName('size-input-lg');

    for(i=0; i < sizes.length; i++)
    {
        sizes[i].addEventListener('click', function() {
            document.getElementById('item-size').value = (this.textContent || this.innerText);
        }, false);
    }

    var materials = document.getElementsByClassName('material-input-lg');

    for(i=0; i < materials.length; i++)
    {
        materials[i].addEventListener('click', function() {
            document.getElementById('item-material').value = (this.textContent || this.innerText);
        }, false);
    }

});