$(function() {
	let token = $("meta[name='_csrf']").attr("content");
	let header = $("meta[name='_csrf_header']").attr("content");
	$(document).ajaxSend(function(e, xhr, options) {
		xhr.setRequestHeader(header, token);
	});
	$(".deleteBtn").on("click", function(evt) {
		let url = $(this).data("href");
		$.ajax({
			type : "POST",
			url : url,
			success : function(data) {
				if (data.error == "success") {
					alert("Produit supprimé avec succès");
					window.location.href = "/admin/produits";
				} else {
					alert("Opération échouée !");
					window.location.href = "/admin/produits";
				}

			}
		});
	});
});