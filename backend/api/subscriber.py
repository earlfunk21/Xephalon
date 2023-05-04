from flask import Blueprint, jsonify, request, abort
from backend.api.auth import token_required
from backend.models import User
from backend.models import db
from backend.models import Payment
from backend.models.subscriber import Subscriber


subscriber_bp = Blueprint("subscriber", __name__, url_prefix="/subscriber")


@subscriber_bp.route("/list/<int:page>", defaults={"page": 1})
@subscriber_bp.route("/list")
def subscriber_list(page=1):
    query = Subscriber.query
    search = request.args.get("search")

    if search is not None:
        query = query.filter(
            Subscriber.first_name.like(f"%{search}%"),
            Subscriber.last_name.like(f"%{search}%"),
            Subscriber.middle_name.like(f"%{search}%"),
        )

    per_page = request.args.get("per_page", 10, type=int)
    pagination = query.paginate(page=page, error_out=False, per_page=per_page)
    subscribers = pagination.items

    data = [
        dict(
            id=subscriber.id,
            name=subscriber.name
        )
        for subscriber in subscribers
    ]

    return jsonify(data)


@subscriber_bp.route("/add", methods=["post"])
@token_required
def add_subscriber(user):
    data = request.json

    first_name: str = data.get("first_name")
    middle_name: str = data.get("middle_name")
    last_name: str = data.get("last_name")
    address: str = data.get("address")
    phone: str = data.get("phone")
    social_media: str = data.get("social_media")
    about: str = data.get("about")

    subscriber = Subscriber(
        first_name=first_name.capitalize(),
        middle_name=middle_name.capitalize(),
        last_name=last_name.capitalize(),
        address=address.capitalize(),
        phone=phone.capitalize(),
        social_media=social_media.capitalize(),
        about=about.capitalize(),
    )
    db.session.add(subscriber)
    db.session.commit()

    data = dict(
        subscriber=str(subscriber),
        subscriber_id=subscriber.id
    )

    return jsonify(data)


@subscriber_bp.route("/get_subscriber/<id>")
@token_required
def get_subscriber(user, id):
    subscriber = Subscriber.query.get_or_404(id)
    data = dict(
        id=subscriber.id,
        first_name=subscriber.first_name,
        middle_name=subscriber.middle_name,
        last_name=subscriber.last_name,
        address=subscriber.address,
        phone=subscriber.phone,
        social_media=subscriber.social_media,
        about=subscriber.about,
    )
    return jsonify(data)
