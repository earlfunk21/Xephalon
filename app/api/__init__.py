from flask import Blueprint


api_bp = Blueprint("api", __name__, url_prefix='/api')


from app.api.user import user_bp
from app.api.auth import auth_bp

api_bp.register_blueprint(user_bp)
api_bp.register_blueprint(auth_bp)