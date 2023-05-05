import json
from app.models import db
import datetime
from flask_sqlalchemy import BaseQuery
from sqlalchemy.ext.hybrid import hybrid_property
from flask import abort, Response


class Query(BaseQuery):
    def get_expired(self, from_date, to_date):
        payments = self.filter(Payment.is_expired == True).filter(Payment.due_date.between(from_date, to_date)).all()
        if payments is None:
            error_message = json.dumps(
                {"message": f"No expired payment from {from_date} to {to_date} not found"}
            )
            abort(Response(error_message, 415))
        return payments

class Payment(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    remarks = db.Column(db.String(80))
    date_paid = db.Column(db.DateTime(timezone=True), default=datetime.datetime.now())
    due_date = db.Column(
        db.DateTime(timezone=True),
        default=datetime.datetime.now() + datetime.timedelta(days=30),
    )
    amount = db.Column(db.Float)

    # relationships
    user_id = db.Column(db.String(10), db.ForeignKey("user.id"))
    user = db.relationship("User", foreign_keys=[user_id])

    subscriber_id = db.Column(db.String(10), db.ForeignKey("subscriber.id"))
    subscriber = db.relationship(
        "Subscriber", back_populates="payments", foreign_keys=[subscriber_id]
    )

    @hybrid_property
    def is_expired(self):
        return datetime.datetime.now() >= self.due_date