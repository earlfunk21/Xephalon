from flask import jsonify, request, Blueprint, current_app
from functools import wraps
from itsdangerous import URLSafeTimedSerializer
from app.models import db
from app.models.user import User

auth_bp = Blueprint("auth", __name__, url_prefix="/auth")


def token_required(f):
    @wraps(f)
    def decorated(*args, **kwargs):
        token = request.headers.get("Authorization")
        if not token:
            return jsonify(message="Token is missing"), 401
        # removing "Bearer" from the token
        token = token.replace("Bearer ", "")
        s = URLSafeTimedSerializer(current_app.config["SECRET_KEY"])
        try:
            data = s.loads(token, max_age=3600)
        except:
            return jsonify(message="Token is invalid or expired"), 401

        user = User.query.get_or_404(data["user_id"])
        return f(user, *args, **kwargs)

    return decorated


@auth_bp.route("/getUser")
@token_required
def checkAuth(user: User):
    return jsonify(user=user.username)


@auth_bp.route("/login/", methods=["POST"])
def login():
    data = request.json
    username = data["username"]
    password = data["password"]

    user = User.query.filter_by(username=username).first()
    if not user or not user.check_password(password):
        return jsonify(message="Invalid username or password"), 401
    s = URLSafeTimedSerializer(current_app.config["SECRET_KEY"])
    token = s.dumps({"user_id": user.id})
    return jsonify(token=token, user=user.username)


@auth_bp.route("/create", methods=["POST"])
def create_user():
    data = request.json
    username = data["username"]
    password = data["password"]

    if User.query.filter_by(username=username).first() is not None:
        return jsonify(message="Username already in use"), 400

    user = User(username=username, password=password)
    db.session.add(user)
    db.session.commit()
    return jsonify(username=username, message="Successfully created")