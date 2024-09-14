package hr.algebra.spacexapp.factory

import android.content.Context
import hr.algebra.spacexapp.dao.SpacexSqlHelper

fun getRepository(context: Context?) = SpacexSqlHelper(context)
