/* ========================================================================= */
/* BE SURE TO COMMENT CODE/IDENTIFY PER PLUGIN CALL */
/* ========================================================================= */
jQuery(function($){
	game();
	
	function game() {
		if ($('#home').length > 0) {
			var gameTitle = $('.game-title').text();
			$('.door').hover(function(e) {
				//do hover stuff
				$('.game-title').text($(this).data('game'));
			}, function(e) {
				//do mouseout stuff
				$('.game-title').text(gameTitle);
			});
		} else if ($('#hangman').length > 0 || $('#mastermind').length > 0) {
		    var datas = $('.game-datas');
		    var status = $(datas).data('status');
		    var player = $(datas).data('player');

			if (player == "no-player" || status == "start" || status == "over") {
				$('.popup.game-status').fadeIn(300);
			}
			$('.menu-trigger').on('click', function() {
				$('.popup.game-menu').fadeIn(300);
			});
			$('.game-rule-trigger').on('click', function() {
				$('.main-menu').hide();
                $('.high-game-list').hide();
                $('.sub-menu').show();
                $('.game-rules').show();
			})
			$('.high-game-list-trigger').on('click', function() {
				$('.main-menu').hide();
                $('.game-rules').hide();
                $('.sub-menu').show();
                $('.high-game-list').show();
			});
			$('.close').on('click', function() {
                $('.sub-menu').hide();
				$('.main-menu').show();
			});
			$('.resume').on('click', function() {
				$('.popup.game-menu').fadeOut(300);
			});
			$('.show-mine').on('click', function() {
			    $('.all-records').hide();
			    $('.my-records').show();
			});
			$('.show-all').on('click', function() {
			    $('.all-records').show();
			    $('.my-records').hide();
			});
		}
		
		if ($('#hangman').length > 0) {
			hangman(datas);
		} else if ($('#mastermind').length > 0) {
			mastermind(datas);
		}
		$('.play-again').on('click', function() {
    		location.reload();
		});
	}
	
	function hangman(datas) {
		$('.form-check-input').on('change', function(e) {
            $('.guess-form').submit();
		});
	}
	
	function mastermind(datas) {
		var size = $(datas).data('size');
		var index = 0;
		$('.game-controller .ball-trigger').on('click', function() {
			if (index < size) {
				var color = $(this).data('color');
				var guess = $(this).data('guess');
				$('.board-center .board-row.active .row-left li').eq(index).find('.ball').addClass('active').attr('data-color', color);
				$('.guess').val($('.guess').val() + guess);
				index ++;
			}
		});
		$('.game-controller .delete').on('click', function() {
			$('.board-center .board-row.active .row-left li .ball').attr('data-color', '').removeClass('active');
            $('.guess').val("");
			index = 0;
		})
	}
});