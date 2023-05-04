from flask import Blueprint


api_bp = Blueprint("api", __name__, url_prefix='/api')


from backend.api.user import user_bp
from backend.api.auth import auth_bp

api_bp.register_blueprint(user_bp)
api_bp.register_blueprint(auth_bp)