from flask import Blueprint, jsonify, request
from backend.api.auth import token_required
from backend.models import User
from backend.models import db


user_bp = Blueprint("user", __name__, url_prefix="/user")


@user_bp.route("/list/<int:page>", defaults={"page": 1})
@user_bp.route("/list")
@token_required
def user_list(user, page=1):
    query = User.query.order_by(db.asc(User.username))
    search = request.args.get("search")
    
    if search is not None:
        query = query.search_username(search)
        
    per_page = request.args.get("per_page", 10)
    pagination = query.paginate(page=page, error_out=False, per_page=per_page)
    users = pagination.items

    data = [
        dict(
            id=user.id,
            username=user.username,
        )
        for user in users
    ]
    return jsonify(data)


@user_bp.route("/get_user")
@token_required
def get_user(user):
    roles = [role.name for role in user.roles]
    data = dict(
        username=user.username,
    )
    return jsonify(data)
