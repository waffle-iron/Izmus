/**
 * The game
 */
(function() {
	var scale = 20;
	var maxStep = 0.05;
	var wobbleSpeed = 8;// for coins
	var wobbleDist = 0.07;// for coins
	var playerXSpeed = 7;
	var gravity = 30;
	var jumpSpeed = 17;
	var otherSprites = document.createElement("img");
	otherSprites.src = "views/game/images/sprites.png";
	var playerSprites = document.createElement("img");
	playerSprites.src = "views/game/images/player.png";
	var flipedPlayerSprites = document.createElement("img");
	flipedPlayerSprites.src = "views/game/images/playerflip.png";
	var playerXOverlap = 4;
	var arrowCodes = {
		37 : "left",
		38 : "up",
		39 : "right"
	};
	var arrows = trackKeys(arrowCodes);
	var simpleLevelPlan = [ "                      ", "                      ",
			"  x              = x  ", "  x         o o    x  ",
			"  x @      xxxxx   x  ", "  xxxxx            x  ",
			"      x!!!!!!!!!!!!x  ", "      xxxxxxxxxxxxxx  ",
			"                      " ];
	var GAME_LEVELS = [
			[
					"                                                                                ",
					"                                                                                ",
					"                                                                                ",
					"                                                                                ",
					"                                                                                ",
					"                                                                                ",
					"                                                                  xxx           ",
					"                                                   xx      xx    xx!xx          ",
					"                                    o o      xx                  x!!!x          ",
					"                                                                 xx!xx          ",
					"                                   xxxxx                          xvx           ",
					"                                                                            xx  ",
					"  x=                                      o o                                x  ",
					"  x                     o                                                    x  ",
					"  x                                      xxxxx                             o x  ",
					"  x          =xx=       o                                                    x  ",
					"  x  @       x  x                                                xxxxx       x  ",
					"  xxxxxxxxxxxx  xxxxxxxxxxxxxxx   xxxxxxxxxxxxxxxxxxxx     xxxxxxx   xxxxxxxxx  ",
					"                              x   x                  x     x                    ",
					"                              x!!!x                  x!!!!!x                    ",
					"                              x!!!x                  x!!!!!x                    ",
					"                              xxxxx                  xxxxxxx                    ",
					"                                                                                ",
					"                                                                                " ],
			[
					"                                      x!!x                        xxxxxxx                                    x!x  ",
					"                                      x!!x                     xxxx     xxxx                                 x!x  ",
					"                                      x!!xxxxxxxxxx           xx           xx                                x!x  ",
					"                                      xx!!!!!!!!!!xx         xx             xx                               x!x  ",
					"                                       xxxxxxxxxx!!x         x                                    o   o   o  x!x  ",
					"                                                xx!x         x     o   o                                    xx!x  ",
					"                                                 x!x         x                                xxxxxxxxxxxxxxx!!x  ",
					"                                                 xvx         x     x   x                        !!!!!!!!!!!!!!xx  ",
					"                                                             xx  |   |   |  xx            xxxxxxxxxxxxxxxxxxxxx   ",
					"                                                              xx!!!!!!!!!!!xx            v                        ",
					"                                                               xxxx!!!!!xxxx                                      ",
					"                                               x     x            xxxxxxx        xxx         xxx                  ",
					"                                               x     x                           x x         x x                  ",
					"                                               x     x                             x         x                    ",
					"                                               x     x                             xx        x                    ",
					"                                               xx    x                             x         x                    ",
					"                                               x     x      o  o     x   x         x         x                    ",
					"               xxxxxxx        xxx   xxx        x     x               x   x         x         x                    ",
					"              xx     xx         x   x          x     x     xxxxxx    x   x   xxxxxxxxx       x                    ",
					"             xx       xx        x o x          x    xx               x   x   x               x                    ",
					"     @       x         x        x   x          x     x               x   x   x               x                    ",
					"    xxx      x         x        x   x          x     x               x   xxxxx   xxxxxx      x                    ",
					"    x x      x         x       xx o xx         x     x               x     o     x x         x                    ",
					"!!!!x x!!!!!!x         x!!!!!!xx     xx!!!!!!!!xx    x!!!!!!!!!!     x     =     x x         x                    ",
					"!!!!x x!!!!!!x         x!!!!!xx       xxxxxxxxxx     x!!!!!!!xx!     xxxxxxxxxxxxx xx  o o  xx                    ",
					"!!!!x x!!!!!!x         x!!!!!x    o                 xx!!!!!!xx !                    xx     xx                     ",
					"!!!!x x!!!!!!x         x!!!!!x                     xx!!!!!!xx  !                     xxxxxxx                      ",
					"!!!!x x!!!!!!x         x!!!!!xx       xxxxxxxxxxxxxx!!!!!!xx   !                                                  ",
					"!!!!x x!!!!!!x         x!!!!!!xxxxxxxxx!!!!!!!!!!!!!!!!!!xx    !                                                  ",
					"!!!!x x!!!!!!x         x!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!xx     !                                                  " ],
			[
					"                                                                                                              ",
					"                                                                                                              ",
					"                                                                                                              ",
					"                                                                                                              ",
					"                                                                                                              ",
					"                                        o                                                                     ",
					"                                                                                                              ",
					"                                        x                                                                     ",
					"                                        x                                                                     ",
					"                                        x                                                                     ",
					"                                        x                                                                     ",
					"                                       xxx                                                                    ",
					"                                       x x                 !!!        !!!  xxx                                ",
					"                                       x x                 !x!        !x!                                     ",
					"                                     xxx xxx                x          x                                      ",
					"                                      x   x                 x   oooo   x       xxx                            ",
					"                                      x   x                 x          x      x!!!x                           ",
					"                                      x   x                 xxxxxxxxxxxx       xxx                            ",
					"                                     xx   xx      x   x      x                                                ",
					"                                      x   xxxxxxxxx   xxxxxxxx              x x                               ",
					"                                      x   x           x                    x!!!x                              ",
					"                                      x   x           x                     xxx                               ",
					"                                     xx   xx          x                                                       ",
					"                                      x   x= = = =    x            xxx                                        ",
					"                                      x   x           x           x!!!x                                       ",
					"                                      x   x    = = = =x     o      xxx       xxx                              ",
					"                                     xx   xx          x                     x!!!x                             ",
					"                              o   o   x   x           x     x                xxv        xxx                   ",
					"                                      x   x           x              x                 x!!!x                  ",
					"                             xxx xxx xxx xxx     o o  x!!!!!!!!!!!!!!x                   vx                   ",
					"                             x xxx x x xxx x          x!!!!!!!!!!!!!!x                                        ",
					"                             x             x   xxxxxxxxxxxxxxxxxxxxxxx                                        ",
					"                             xx           xx                                         xxx                      ",
					"  xxx                         x     x     x                                         x!!!x                xxx  ",
					"  x x                         x    xxx    x                                          xxx                 x x  ",
					"  x                           x    xxx    xxxxxxx                        xxxxx                             x  ",
					"  x                           x           x                              x   x                             x  ",
					"  x                           xx          x                              x x x                             x  ",
					"  x                                       x       |xxxx|    |xxxx|     xxx xxx                             x  ",
					"  x                xxx             o o    x                              x         xxx                     x  ",
					"  x               xxxxx       xx          x                             xxx       x!!!x          x         x  ",
					"  x               oxxxo       x    xxx    x                             x x        xxx          xxx        x  ",
					"  x                xxx        xxxxxxxxxxxxx  x oo x    x oo x    x oo  xx xx                    xxx        x  ",
					"  x      @          x         x           x!!x    x!!!!x    x!!!!x    xx   xx                    x         x  ",
					"  xxxxxxxxxxxxxxxxxxxxxxxxxxxxx           xxxxxxxxxxxxxxxxxxxxxxxxxxxxx     xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx  ",
					"                                                                                                              ",
					"                                                                                                              " ],
			[
					"                                                                                                  xxx x       ",
					"                                                                                                      x       ",
					"                                                                                                  xxxxx       ",
					"                                                                                                  x           ",
					"                                                                                                  x xxx       ",
					"                          o                                                                       x x x       ",
					"                                                                                             o o oxxx x       ",
					"                   xxx                                                                                x       ",
					"       !  o  !                                                xxxxx xxxxx xxxxx xxxxx xxxxx xxxxx xxxxx       ",
					"       x     x                                                x   x x   x x   x x   x x   x x   x x           ",
					"       x= o  x            x                                   xxx x xxx x xxx x xxx x xxx x xxx x xxxxx       ",
					"       x     x                                                  x x   x x   x x   x x   x x   x x     x       ",
					"       !  o  !            o                                  xxxx xxxxx xxxxx xxxxx xxxxx xxxxx xxxxxxx       ",
					"                                                                                                              ",
					"          o              xxx                              xx                                                  ",
					"                                                                                                              ",
					"                                                                                                              ",
					"                                                      xx                                                      ",
					"                   xxx         xxx                                                                            ",
					"                                                                                                              ",
					"                          o                                                     x      x                      ",
					"                                                          xx     xx                                           ",
					"             xxx         xxx         xxx                                 x                  x                 ",
					"                                                                                                              ",
					"                                                                 ||                                           ",
					"  xxxxxxxxxxx                                                                                                 ",
					"  x         x o xxxxxxxxx o xxxxxxxxx o xx                                                x                   ",
					"  x         x   x       x   x       x   x                 ||                  x     x                         ",
					"  x  @      xxxxx   o   xxxxx   o   xxxxx                                                                     ",
					"  xxxxxxx                                     xxxxx       xx     xx     xxx                                   ",
					"        x=                  =                =x   x                     xxx                                   ",
					"        xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx   x!!!!!!!!!!!!!!!!!!!!!xxx!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!",
					"                                                  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
					"                                                                                                              " ] ];
	GAME_LEVELS.push(simpleLevelPlan);
	var actorChars = {
		"@" : Player,
		"o" : Coin,
		"=" : Lava,
		"|" : Lava,
		"v" : Lava
	};
	/*----------------------------------------------------------------------------------------------------*/
	function trackKeys(codes) {
		var pressed = Object.create(null);
		function handler(event) {
			if (codes.hasOwnProperty(event.keyCode)) {
				var down = event.type == "keydown";
				pressed[codes[event.keyCode]] = down;
				event.preventDefault();
			}
		}
		addEventListener("keydown", handler);
		addEventListener("keyup", handler);
		return pressed;
	}
	;
	/*----------------------------------------------------------------------------------------------------*/

	function runAnimation(frameFunc) {
		var lastTime = null;
		function frame(time) {
			var stop = false;
			if (lastTime != null) {
				var timeStep = Math.min(time - lastTime, 100) / 1000;
				stop = frameFunc(timeStep) === false;
			}
			lastTime = time;
			if (!stop) {
				requestAnimationFrame(frame);
			}
		}
		requestAnimationFrame(frame);
	}
	;
	/*----------------------------------------------------------------------------------------------------*/

	function runLevel(level, Display, andThen) {
		var oldBody = (document.body.getElementsByClassName("mainGame"))[0];
		var body = elt("div", "mainGame");
		body.id = "page-wrapper";
		var rowBody = elt("div", "row");
		var headerRow = elt("div", "row");
		var headerBody = elt("div", "col-lg-12");
		var headerText = elt("h1", "page-header");
		headerText.style.marginTop = '60px';
		var columnBody = elt("div", "col-lg-12");
		headerText.innerHTML = "The Game";
		body.appendChild(headerRow);
		headerRow.appendChild(headerBody);
		headerBody.appendChild(headerText);
		body.appendChild(rowBody);
		rowBody.appendChild(columnBody);
		if (oldBody) {
			document.body.replaceChild(body, oldBody);
		} else {
			document.body.appendChild(body);
		}
		var display = new Display(columnBody, level);
		runAnimation(function(step) {
			level.animate(step, arrows);
			display.drawFrame(step, level);
			if (level.isFinished()) {
				display.clear();
				if (andThen) {
					andThen(level.status);
				}
				return false;
			}
		});
	}
	;
	/*----------------------------------------------------------------------------------------------------*/

	function runGame(plans, Display) {
		function startLevel(n) {
			runLevel(new Level(plans[n]), Display, function(status) {
				if (status == "lost") {
					startLevel(n);
				} else if (n < plans.length - 1) {
					startLevel(n + 1);
				} else {
					alert("You Win!!!");
				}
			});
		}
		;
		startLevel(0);
	}
	;
	/*----------------------------------------------------------------------------------------------------*/

	/**
	 * Create a level
	 * 
	 * @param plan
	 */
	function Level(plan) {
		this.width = plan[0].length;
		this.height = plan.length;
		this.grid = [];
		this.actors = [];
		for (var y = 0; y < this.height; y++) {
			var line = plan[y];
			var gridLine = [];
			for (var x = 0; x < this.width; x++) {
				var ch = line[x];
				var fieldType = null;
				var Actor = actorChars[ch];
				if (Actor) {
					this.actors.push(new Actor(new Vector(x, y), ch));
				} else if (ch == "x") {
					fieldType = "wall";
				} else if (ch == "!") {
					fieldType = "lava";
				}
				gridLine.push(fieldType);
			}
			this.grid.push(gridLine);
		}
		this.player = this.actors.filter(function(actor) {
			return actor.type == "player";
		})[0];
		this.status = this.finishDelay = null;
	}
	;
	/*----------------------------------------------------------------------------------------------------*/

	/**
	 * Prototype giving back if the level is finished.
	 * 
	 * @returns {Boolean}
	 */
	Level.prototype.isFinished = function() {
		return this.status != null && this.finishDelay < 0;
	};
	/*----------------------------------------------------------------------------------------------------*/

	/**
	 * Gives back the obstacle
	 * 
	 * @param pos
	 * @param size
	 * @returns
	 */
	Level.prototype.obstacleAt = function(pos, size) {
		var xStart = Math.floor(pos.x);
		var xEnd = Math.ceil(pos.x + size.x);
		var yStart = Math.floor(pos.y);
		var yEnd = Math.ceil(pos.y + size.y);
		if (xStart < 0 || xEnd > this.width || yStart < 0) {
			return "wall";
		}
		if (yEnd > this.height) {
			return "lava";
		}
		for (var y = yStart; y < yEnd; y++) {
			for (var x = xStart; x < xEnd; x++) {
				var fieldType = this.grid[y][x];
				if (fieldType)
					return fieldType;
			}
		}
	};
	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * @param actor
	 */
	Level.prototype.actorAt = function(actor) {
		for (var i = 0; i < this.actors.length; i++) {
			var other = this.actors[i];
			if (other != actor && actor.pos.x + actor.size.x > other.pos.x
					&& actor.pos.x < other.pos.x + other.size.x
					&& actor.pos.y + actor.size.y > other.pos.y
					&& actor.pos.y < other.pos.y + other.size.y) {
				return other;
			}
		}
	};
	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * @param step
	 * @param keys
	 */
	Level.prototype.animate = function(step, keys) {
		if (this.status != null) {
			this.finishDelay -= step;
		}
		while (step > 0) {
			var thisStep = Math.min(step, maxStep);
			this.actors.forEach(function(actor) {
				actor.act(thisStep, this, keys);
			}, this);
			step -= thisStep;
		}
	};
	/*----------------------------------------------------------------------------------------------------*/

	Level.prototype.act = function(step, level) {
		var newPos = this.pos.plus(this.speed.times(step));
		if (!level.obstacleAt(newPos, this.size)) {
			this.pos = newPos;
		} else if (this.repeatPos) {
			this.pos = this.repeatPos;
		} else {
			this.speed = this.speed.times(-1);
		}
	};
	/*----------------------------------------------------------------------------------------------------*/

	/**
	 * @param type
	 * @param actor
	 */
	Level.prototype.playerTouched = function(type, actor) {
		if (type == "lava" && this.status == null) {
			this.status = "lost";
			this.finishDelay = 1;
		} else if (type == "coin") {
			this.actors = this.actors.filter(function(other) {
				return other != actor;
			});
			if (!this.actors.some(function(actor) {
				return actor.type == "coin";
			})) {
				this.status = "won";
				this.finishDelay = 1;
			}
		}
	};
	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * @param x
	 * @param y
	 */
	function Vector(x, y) {
		this.x = x;
		this.y = y;
	}
	;
	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * @param factor
	 * @returns {Vector}
	 */
	Vector.prototype.plus = function(other) {
		return new Vector(this.x + other.x, this.y + other.y);
	};
	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * @param factor
	 * @returns {Vector}
	 */
	Vector.prototype.times = function(factor) {
		return new Vector(this.x * factor, this.y * factor);
	};
	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * @param pos
	 */
	function Player(pos) {
		this.pos = pos.plus(new Vector(0, -0.5));
		this.size = new Vector(0.8, 1.5);
		this.speed = new Vector(0, 0);
	}
	;
	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * 
	 */
	Player.prototype.type = "player";
	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * @param step
	 * @param level
	 * @param keys
	 */
	Player.prototype.moveX = function(step, level, keys) {
		if (level.status != "lost") {
			this.speed.x = 0;
			if (keys.left)
				this.speed.x -= playerXSpeed;
			if (keys.right)
				this.speed.x += playerXSpeed;
			var motion = new Vector(this.speed.x * step, 0);
			var newPos = this.pos.plus(motion);
			var obstacle = level.obstacleAt(newPos, this.size);
			if (obstacle) {
				level.playerTouched(obstacle);
			} else {
				this.pos = newPos;
			}
		}
	};
	/*----------------------------------------------------------------------------------------------------*/

	/**
	 * @param step
	 * @param level
	 * @param keys
	 */
	Player.prototype.moveY = function(step, level, keys) {
		if (level.status != "lost") {
			this.speed.y += step * gravity;
			var motion = new Vector(0, this.speed.y * step);
			var newPos = this.pos.plus(motion);
			var obstacle = level.obstacleAt(newPos, this.size);
			if (obstacle) {
				level.playerTouched(obstacle);
				if (keys.up && this.speed.y > 0) {
					this.speed.y = -jumpSpeed;
				} else {
					this.speed.y = 0;
				}
			} else {
				this.pos = newPos;
			}
		}
	};
	/*----------------------------------------------------------------------------------------------------*/

	/**
	 * @param step
	 * @param level
	 * @param keys
	 */
	Player.prototype.act = function(step, level, keys) {
		if (level.status == "lost") {
			this.pos.y += step;
			this.size.y -= step;
		} else {
			this.moveX(step, level, keys);
			this.moveY(step, level, keys);
			var otherActor = level.actorAt(this);
			if (otherActor) {
				level.playerTouched(otherActor.type, otherActor);
			}
		}
	};
	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * @param pos
	 */
	function Lava(pos, ch) {
		this.pos = pos;
		this.size = new Vector(1, 1);
		if (ch == "=") {
			this.speed = new Vector(2, 0);
		} else if (ch == "|") {
			this.speed = new Vector(0, 2);
		} else if (ch == "v") {
			this.speed = new Vector(0, 3);
			this.repeatPos = pos;
		}
	}
	;
	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * 
	 */
	Lava.prototype.type = "lava";
	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * @param step
	 * @param level
	 */
	Lava.prototype.act = function(step, level) {
		var newPos = this.pos.plus(this.speed.times(step));
		if (!level.obstacleAt(newPos, this.size)) {
			this.pos = newPos;
		} else if (this.repeatPos) {
			this.pos = this.repeatPos;
		} else {
			this.speed = this.speed.times(-1);
		}
	};
	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * @param pos
	 */
	function Coin(pos) {
		this.basePos = this.pos = pos.plus(new Vector(0.2, 0.1));
		this.size = new Vector(0.6, 0.6);
		this.wobble = Math.random() * Math.PI * 2;
	}
	;
	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * @param step
	 */
	Coin.prototype.act = function(step) {
		this.wobble += step * wobbleSpeed;
		var wobblePos = Math.sin(this.wobble) * wobbleDist;
		this.pos = this.basePos.plus(new Vector(0, wobblePos));
	};
	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * 
	 */
	Coin.prototype.type = "coin";
	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * @param name
	 * @param className
	 */
	function elt(name, className) {
		var elt = document.createElement(name);
		if (className)
			elt.className = className;
		return elt;
	}
	;
	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * @param parent
	 * @param level
	 */
	function DOMDisplay(parent, level) {
		this.wrap = parent.appendChild(elt("div", "game"));
		this.level = level;
		this.wrap.appendChild(this.drawBackground());
		this.drawFrame();
	}
	;
	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * @returns {___anonymous30076_30080}
	 */
	DOMDisplay.prototype.drawBackground = function() {
		var table = elt("table", "background");
		table.style.width = this.level.width * scale + "px";
		this.level.grid.forEach(function(row) {
			var rowElt = table.appendChild(elt("tr"));
			rowElt.style.height = scale + "px";
			row.forEach(function(type) {
				rowElt.appendChild(elt("td", type));
			});
		});
		return table;
	};
	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * @returns
	 */
	DOMDisplay.prototype.drawActors = function() {
		var wrap = elt("div");
		this.level.actors.forEach(function(actor) {
			var rect = wrap.appendChild(elt("div", "actor " + actor.type));
			rect.style.width = actor.size.x * scale + "px";
			rect.style.height = actor.size.y * scale + "px";
			rect.style.left = actor.pos.x * scale + "px";
			rect.style.top = actor.pos.y * scale + "px";
		});
		return wrap;
	};
	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * 
	 */
	DOMDisplay.prototype.drawFrame = function() {
		if (this.actorLayer) {
			this.wrap.removeChild(this.actorLayer);
		}
		this.actorLayer = this.wrap.appendChild(this.drawActors());
		this.wrap.className = "game " + (this.level.status || "");
		this.scrollPlayerIntoView();
	};
	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * 
	 */
	DOMDisplay.prototype.scrollPlayerIntoView = function() {
		var width = this.wrap.clientWidth;
		var height = this.wrap.clientHeigt;
		var margin = width / 3;
		var left = this.wrap.scrollLeft;
		var right = left + width;
		var top = this.wrap.scrollTop;
		var bottom = top + height;
		var player = this.level.player;
		var center = player.pos.plus(player.size.times(0.5)).times(scale);
		if (center.x < left + margin) {
			this.wrap.scrollLeft = center.x - margin;
		} else if (center.x > right - margin) {
			this.wrap.scrollLeft = center.x + margin - width;
		}
		if (center.y < top + margin) {
			this.wrap.scrollTop = center.y - margin;
		} else if (center.y > bottom - margin) {
			this.wrap.scrollTop = center.y + margin - height;
		}
	};
	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * 
	 */
	DOMDisplay.prototype.clear = function() {
		this.wrap.parentNode.removeChild(this.wrap);
	};
	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * @param parent
	 * @param level
	 */
	function CanvasDisplay(parent, level) {
		this.canvas = document.createElement("canvas");
		this.canvas.width = Math.min(600, level.width * scale);
		this.canvas.height = Math.min(450, level.height * scale);
		parent.appendChild(this.canvas);
		this.cx = this.canvas.getContext("2d");
		this.level = level;
		this.animationTime = 0;
		this.flipPlayer = false;
		this.viewport = {
			left : 0,
			top : 0,
			width : this.canvas.width / scale,
			height : this.canvas.height / scale
		};
		this.drawFrame(0);
	}
	;
	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * 
	 */
	CanvasDisplay.prototype.clear = function() {
		this.canvas.parentNode.removeChild(this.canvas);
	};
	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * @param step
	 */
	CanvasDisplay.prototype.drawFrame = function(step, level) {
		this.animationTime += step;
		this.updateViewport();
		this.clearDisplay();
		this.drawBackground();
		this.drawActors(level);
	};
	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * 
	 */
	CanvasDisplay.prototype.updateViewport = function() {
		var view = this.viewport;
		var margin = view.width / 3;
		var player = this.level.player;
		var center = player.pos.plus(player.size.times(0.5));
		if (center.x < view.left + margin) {
			view.left = Math.max(center.x - margin, 0);
		} else if (center.x > view.left + view.width - margin) {
			view.left = Math.min(center.x + margin - view.width,
					this.level.width - view.width);
		}
		if (center.y < view.top + margin) {
			view.top = Math.max(center.y - margin, 0);
		} else if (center.y > view.top + view.height - margin) {
			view.top = Math.min(center.y + margin - view.height,
					this.level.height - view.height);
		}
	};
	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * 
	 */
	CanvasDisplay.prototype.clearDisplay = function() {
		if (this.level.status == "won") {
			this.cx.fillStyle = "rgb(68, 191, 255)";
		} else if (this.level.status == "lost") {
			this.cx.fillStyle = "rgb(44, 125, 214)";
		} else {
			this.cx.fillStyle = "rgb(52, 166, 251)";
		}
		this.cx.fillRect(0, 0, this.canvas.width, this.canvas.height);
	};
	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * 
	 */
	CanvasDisplay.prototype.drawBackground = function() {
		var view = this.viewport;
		var xStart = Math.floor(view.left);
		var xEnd = Math.ceil(view.left + view.width);
		var yStart = Math.floor(view.top);
		var yEnd = Math.ceil(view.top + view.height);
		for (var y = yStart; y < yEnd; y++) {
			for (var x = xStart; x < xEnd; x++) {
				var tile = this.level.grid[y][x];
				if (tile == null)
					continue;
				var screenX = (x - view.left) * scale;
				var screenY = (y - view.top) * scale;
				var tileX = tile == "lava" ? scale : 0;
				this.cx.drawImage(otherSprites, tileX, 0, scale, scale,
						screenX, screenY, scale, scale);
			}
		}
	};
	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	CanvasDisplay.prototype.drawPlayer = function(x, y, width, height, level) {
		var sprite = 8;
		var player = this.level.player;
		var sprites = playerSprites;
		width += playerXOverlap * 2;
		x -= playerXOverlap;
		if (player.speed.x != 0) {
			this.flipPlayer = player.speed.x < 0;
		}
		if (player.speed.y != 0 && level.status != "lost") {
			sprite = 9;
		} else if (player.speed.x != 0 && level.status != "lost") {
			sprite = Math.floor(this.animationTime * 12) % 8;
		}
		this.cx.save();
		if (this.flipPlayer) {
			sprites = flipedPlayerSprites;
			sprite = 9 - sprite;
		}
		this.cx.drawImage(sprites, sprite * width, 0, width, height, x, y,
				width, height);
		this.cx.restore();
	};
	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * 
	 */
	CanvasDisplay.prototype.drawActors = function(level) {
		this.level.actors.forEach(function(actor) {
			var width = actor.size.x * scale;
			var height = actor.size.y * scale;
			var x = (actor.pos.x - this.viewport.left) * scale;
			var y = (actor.pos.y - this.viewport.top) * scale;
			if (actor.type == "player") {
				this.drawPlayer(x, y, width, height, level);
			} else {
				var tileX = (actor.type == "coin" ? 2 : 1) * scale;
				this.cx.drawImage(otherSprites, tileX, 0, width, height, x, y,
						width, height);
			}
		}, this);
	};
	/*----------------------------------------------------------------------------------------------------*/
	runGame(GAME_LEVELS, CanvasDisplay);
})();
