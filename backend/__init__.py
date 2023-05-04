from flask import Flask
from config import ProductionConfig, DevelopmentConfig

from flask_migrate import Migrate
from backend.models import db



def create_app(config=DevelopmentConfig):
    app = Flask(__name__)
    app.config.from_object(config)
    # Blueprints
    blueprints(app)

    extensions(app)
    return app


def blueprints(app: Flask):
    from backend.api import api_bp
    app.register_blueprint(api_bp)

    from backend.admin import admin_bp
    app.register_blueprint(admin_bp)
    

def extensions(app: Flask):
    db.init_app(app)
    Migrate(app, db)
