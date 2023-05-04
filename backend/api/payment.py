from datetime import datetime
from backend.models.payment import Payment
from backend.api.auth import token_required
from flask import Blueprint, request, jsonify

from backend.models.subscriber import Subscriber


payment_bp = Blueprint("payment", __name__, url_prefix="/payment")


@payment_bp.route("/list/<int:page>", defaults={"page": 1})
@payment_bp.route("/list")
@token_required
def payment_list(page=1):

    from_date_iso = request.args.get("from_date")
    to_date_iso = request.args.get("to_date")
    from_date = datetime.fromisoformat(from_date_iso)
    to_date = datetime.fromisoformat(to_date_iso)
    search = request.args.get("search")
    per_page = request.args.get("per_page", 10, type=int)

    query = Payment.query.filter(Payment.date_paid.between(from_date, to_date))

    if search is not None:
        query = query.join(Subscriber).filter(
            Subscriber.first_name.like(f"%{search}%"),
            Subscriber.last_name.like(f"%{search}%"),
            Subscriber.middle_name.like(f"%{search}%"),
        )

    pagination = query.paginate(page=page, error_out=False, per_page=per_page)
    payments = pagination.items

    data = [
        dict(
            id=payment.id,
            remarks=payment.remarks,
            date_paid=payment.date_paid,
            due_date=payment.due_date,
            amount=payment.amount,
            user_id=payment.user_id,
            subscriber_id=payment.subscriber_id,
        )
        for payment in payments
    ]

    return jsonify(data)
