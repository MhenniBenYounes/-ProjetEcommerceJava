// Remember Me avec jquery pour les visiteurs coté client

// Cela permet d'empêcher tout code jQuery de s'exécuter avant que le document ne soit chargé
$(document).ready(function() {

	if (localStorage.rememberMe && localStorage.rememberMe != '') {
		$('#rememberMe').attr('checked', 'checked');
		$('#email').val(localStorage.email);
		$('#password').val(localStorage.password);
	} else {
		$('#rememberMe').removeAttr('checked');
		$('#email').val('');
		$('#password').val('');
	}

	$('#login').click(function() {

		if ($('#rememberMe').is(':checked')) {
			localStorage.email = $('#email').val();
			localStorage.password = $('#password').val();
			localStorage.rememberMe = $('#rememberMe').val();
		} else {
			localStorage.email = '';
			localStorage.password = '';
			localStorage.rememberMe = '';
		}
	});

});
